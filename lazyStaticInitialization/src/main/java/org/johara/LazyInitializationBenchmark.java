/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

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
