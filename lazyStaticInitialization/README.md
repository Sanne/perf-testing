# Caching of Lazy Initializated objects with Safe Publication

## Summary

One performance optimization pattern that can improve application throughput, mean response times memory utilization is **Caching of Lazily-Instantiated "Expensive" objects**.  

Adopting this pattern means that great care needs to be taken not to publish a partially constructed object instances, while ensuring the object is only instantiated once. Here we investigate performance characteristics of different methods for safe publication of a lazily instantiated cached objects.

The fastest and easiest to reason about method to lazily-instantiate a cachable object is to use the *Static Helper Pattern*. In this test the average *Static Helper* operation took **89.4%** to **91.6%** (depending on contention level) of the time to complete compared to *Double Checked Locking*


## Patterns 

Expensive objects can either take computation time, or use memory.  If you have repeated initialization of expensive ephemeral objects in your hot code path, one technique to improve application throughput/responsiveness is to cache the expensive object when it is first instantiated.  This is especially true if you object is also immutable. 

### Direct Instantiation

example: [DirectInstantiation.java](src/main/java/org/johara/provider/DirectInstantiation.java)

The following example would initialize and discard the `ExpensiveObject` instance with each invocation.
```java
    {
    ...
        ExpensiveObject expensiveObject = ExpensiveObjectFactory.getExpensiveObject();
    ...
    }

``` 

### Eargerly Cached

example: [CachedEagerInstantiation.java](src/main/java/org/johara/provider/CachedEagerInstantiation.java)

The following example publishes a cached instance of `ExpensiveObject` safely (although no guarantee can be made about thread safe access of `expensiveObject` fields), however `ExpensiveObject` is instantiated eagerly when your class is loaded;
```java
    private final ExpensiveObject expensiveObject = ExpensiveObjectFactory.getExpensiveObject();
    
    {
    ...
        ExpensiveObject object = expensiveObject;
    ...
    }
```

### Double Checked Locking

example: [DclStaticConfigProvider.java](src/main/java/org/johara/provider/DclStaticConfigProvider.java)

Sometimes your code does not always need to instantiate an instance of `ExpensiveObject` but can wait until an instance of `ExpensiveObject` is required.  Waiting until you need an instance before instantiating is called *Lazy Idealization*.  One issue with *Lazy Initialization* is the field used to cache the instance of `ExpensiveObject` needs to be published safely.

One strategy for safely publishing a cached, lazily-loaded object is to use Double Checked Locking [^1];

```java
    private static volatile ExpensiveObject expensiveObject;
    
    {
    ...
        ExpensiveObject object = getExpensiveObject();
    ...
    }

    ExpensiveObject getExpensiveObject(){
        if (expensiveObject == null) {
            synchronized (SomeLock.class) {
                if (expensiveObject == null) {
                    expensiveObject = ExpensiveObjectFactory.getExpensiveObject();
                }
            }
        }
        return expensiveObject;
    }
```

Double Checked Locking works from JDK1.5+ but comes with some caveats;

 -  expensiveObject has to be defined as `volatile` to ensure that all subsequent reads from different threads see the safely published instance.
 -  expensiveObject is mutable. It is possible to update `private static volatile ExpensiveObject expensiveObject` after it has been initialized. This may be a requirement, and if it is a requirement, all writes to `expensiveObject` need to be guarded by the same lock `synchronized (SomeLock.class)`.

There are some variations on the above theme, for additional implementations please refer to;
 
 - [DclInstanceConfigProvider.java](src/main/java/org/johara/provider/DclInstanceConfigProvider.java) :
 - [DclLocalRefInstanceConfigProvider.java](src/main/java/org/johara/provider/DclLocalRefInstanceConfigProvider.java) :
 - [DclLocalRefStaticConfigProvider.java](src/main/java/org/johara/provider/DclLocalRefStaticConfigProvider.java) :
 - [DclVarHandleInstanceConfigProvider.java](src/main/java/org/johara/provider/DclVarHandleInstanceConfigProvider.java) :

### Static Helper Class
#### Cached, Lazily Instantiated with Helper Class

example: [HelperStaticConfigProvider.java](src/main/java/org/johara/provider/HelperStaticConfigProvider.java)


If the object your are instantiating can be assigned to a static field in a class, e.g. a config object, another pattern is to use the safety guarantees of the `ClassLoader` to safely publish a lazily-loaded cached instance

```java

    {
    ...
        ExpensiveObject object = getExpensiveObject();
    ...
    }
    
    ExpensiveObject getExpensiveObject(){
        return LazyConfigInitializer.expensiveObject;
    }

    private static class LazyConfigInitializer {
        static final String expensiveObject = ExpensiveObjectFactory.getExpensiveObject();
    }
```
 
 
[^1]: https://en.wikipedia.org/wiki/Double-checked_locking

## Performance Results

A [JMH](https://openjdk.java.net/projects/code-tools/jmh/) microbenchmark was created to measure the mean operation time to retrive a mock configuration item, [LazyInitializationBenchmark.java](src/main/java/org/johara/LazyInitializationBenchmark.java)

## 32 Threads [^2]
```shell script
$ java -jar ./target/benchmarks.jar  -t 32
```

|Benchmark                                   |Mode  |Cnt  |Score   |Error  |Units|
| --- | ---| --- | --- | --- | ---|
| **Direct Instantiation** | | | | | |
|LazyInitializationBenchmark.directProvider               |avgt   |10   |564.606 |± 3.276  |ns/op|
| **Eager Instantiation** | | | | | |
|LazyInitializationBenchmark.eagerCachedInstanceProvider  |avgt   |10   |7.417   |± 0.074  |ns/op|
|LazyInitializationBenchmark.eagerCachedStaticProvider    |avgt   |10   |6.672   |± 0.086  |ns/op|
| **Lazy Instantiation** | | | | | |
|LazyInitializationBenchmark.dclInstanceConfigTest        |avgt   |10   |7.768   |± 0.063  |ns/op|
|LazyInitializationBenchmark.dclLocalRefInstanceProvider  |avgt   |10   |7.646   |± 0.087  |ns/op|
|LazyInitializationBenchmark.dclLocalRefStaticProvider    |avgt   |10   |7.262   |± 0.079  |ns/op|
|LazyInitializationBenchmark.dclStaticProvider            |avgt   |10   |7.334   |± 0.100  |ns/op|
|LazyInitializationBenchmark.dclVarHandleRefProvider      |avgt   |10   |7.758   |± 0.064  |ns/op|
|LazyInitializationBenchmark.finalInstanceConfigProvider  |avgt   |10   |7.751   |± 0.074  |ns/op|
|LazyInitializationBenchmark.helperStaticProvider         |avgt   |10   |6.718   |± 0.103  |ns/op|

### 64 Threads [^2]

```shell script
$ java -jar ./target/benchmarks.jar  -t 32
```

|Benchmark                                   |Mode  |Cnt  |Score   |Error  |Units|
| --- | ---| --- | --- | --- | ---|
| **Direct Instantiation** | | | | | |
|LazyInitializationBenchmark.directProvider               |avgt   |10  |1179.821 |± 13.692 |ns/op
| **Eager Instantiation** | | | | | |
|LazyInitializationBenchmark.eagerCachedInstanceProvider  |avgt   |10  |15.882   |± 0.959  |ns/op
|LazyInitializationBenchmark.eagerCachedStaticProvider    |avgt   |10  |13.700   |± 0.333  |ns/op
| **Lazy Instantiation** | | | | | |
|LazyInitializationBenchmark.dclInstanceConfigTest        |avgt   |10  |16.772   |± 0.990  |ns/op
|LazyInitializationBenchmark.dclLocalRefInstanceProvider  |avgt   |10  |16.545   |± 1.185  |ns/op
|LazyInitializationBenchmark.dclLocalRefStaticProvider    |avgt   |10  |16.161   |± 0.448  |ns/op
|LazyInitializationBenchmark.dclVarHandleRefProvider      |avgt   |10  |15.891   |± 0.322  |ns/op
|LazyInitializationBenchmark.dclStaticProvider            |avgt   |10  |15.746   |± 1.104  |ns/op
|LazyInitializationBenchmark.finalInstanceConfigProvider  |avgt   |10  |19.264   |± 0.772  |ns/op
|LazyInitializationBenchmark.helperStaticProvider         |avgt   |10  |14.078   |± 0.356  |ns/op


[^2]: System under test; 32 core x Intel® Xeon® CPU E5-2640 v3 @ 2.60GHz; Red Hat Enterprise Linux Server release 7.6 (3.10.0-693.25.2.el7.x86_64);  262GB


## Conclusion

If you are trying to improve your application throughput rates, response times or memory utilization performance by caching lazily-loaded expensive to instantiate objects, there are many patterns that allow you to achieve this whilst also safely publishing the cached object instance.

Reasoning about safe publication of the lazy instantiated object can be difficult. Care needs to be taken not to publish a partially constructed object instance, while ensuring the object is only instantiated once.  It is easy to incorrectly publish a partially constructed instance using Double Checked Locking, and care needs to be taken to ensure that your implementation is working as expected.

The fastest and easiest to reason about method to lazily-instantiate a cachable object is to use the *Static Helper Pattern*. In this test the average *Static Helper* operation took 89.4% to 91.6% (depending on contention level) of the time to complete compared to *Double Checked Locking*
