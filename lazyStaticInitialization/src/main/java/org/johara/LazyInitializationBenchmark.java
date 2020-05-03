package org.johara;

import org.johara.provider.DclInstanceConfigProvider;
import org.johara.provider.DclLocalRefInstanceConfigProvider;
import org.johara.provider.DclLocalRefStaticConfigProvider;
import org.johara.provider.DclStaticConfigProvider;
import org.johara.provider.DclVarHandleInstanceConfigProvider;
import org.johara.provider.DirectInstantiationProvider;
import org.johara.provider.EagerCachedInstanceInstantiationProvider;
import org.johara.provider.EagerCachedStaticInstantiationProvider;
import org.johara.provider.FinalInstanceConfigProvider;
import org.johara.provider.HelperStaticConfigProvider;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LazyInitializationBenchmark {

    @State(Scope.Benchmark)
    public static class SharedState {
        public DclVarHandleInstanceConfigProvider dclVarHandleNonStaticConfigProvider = new DclVarHandleInstanceConfigProvider();
        public DclInstanceConfigProvider dclInstanceConfigProvider = new DclInstanceConfigProvider();
        public DclLocalRefInstanceConfigProvider dclLocalRefInstanceConfigProvider = new DclLocalRefInstanceConfigProvider();
        public DirectInstantiationProvider directInstantiation = new DirectInstantiationProvider();
        public FinalInstanceConfigProvider finalInstanceConfigProvider = new FinalInstanceConfigProvider();
        public EagerCachedInstanceInstantiationProvider eagerCachedInstanceInstantiation = new EagerCachedInstanceInstantiationProvider();

    }

    @Benchmark
    public String dclInstanceProvider(SharedState state) { return state.dclInstanceConfigProvider.getDefaultConfigValue(); }

    @Benchmark
    public String dclLocalRefInstanceProvider(SharedState state) {
        return state.dclLocalRefInstanceConfigProvider.getDefaultConfigValue();
    }

    @Benchmark
    public String dclLocalRefStaticProvider(SharedState state) {
        return DclLocalRefStaticConfigProvider.getDefaultConfigValue();
    }

    @Benchmark
    public String dclStaticProvider(SharedState state) {
        return DclStaticConfigProvider.getDefaultConfigValue();
    }

    @Benchmark
    public String dclVarHandleRefProvider(SharedState state) {
        return state.dclVarHandleNonStaticConfigProvider.getDefaultConfigValue();
    }

    @Benchmark
    public String directProvider(SharedState state) {
        return state.directInstantiation.getDefaultConfigValue();
    }

    @Benchmark
    public String eagerCachedInstanceProvider(SharedState state) {
        return state.eagerCachedInstanceInstantiation.getDefaultConfigValue();
    }

    @Benchmark
    public String eagerCachedStaticProvider(SharedState state) {
        return EagerCachedStaticInstantiationProvider.getDefaultConfigValue();
    }

    @Benchmark
    public String finalInstanceProvider(SharedState state) {
        return state.finalInstanceConfigProvider.getDefaultConfigValue();
    }

    @Benchmark
    public String helperStaticProvider(SharedState state) {
        return HelperStaticConfigProvider.getDefaultConfigValue();
    }

}
