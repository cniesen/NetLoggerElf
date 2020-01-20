package com.niesen.netloggerelf;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class NetLoggerElfRunner implements CommandLineRunner, ExitCodeGenerator {

    private final NetLoggerElf netLoggerElf;

    private final IFactory factory; // auto-configured to inject PicocliSpringFactory

    private int exitCode;

    public NetLoggerElfRunner(NetLoggerElf netLoggerElf, IFactory factory) {
        this.netLoggerElf = netLoggerElf;
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(netLoggerElf, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}