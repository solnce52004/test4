package dev.example.dao.benchmarks;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class UserNameState{

    @Param({"Петр", "Bob"})
    public String userName;
}
