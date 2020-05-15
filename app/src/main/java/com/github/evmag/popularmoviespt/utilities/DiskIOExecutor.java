package com.github.evmag.popularmoviespt.utilities;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskIOExecutor {
    private static DiskIOExecutor sInstance;

    private final Executor mDiskIOExecutor;

    private DiskIOExecutor() {
        mDiskIOExecutor = Executors.newSingleThreadExecutor();
    }

    public static DiskIOExecutor getInstance() {
        if (sInstance == null) {
            sInstance = new DiskIOExecutor();
        }

        return sInstance;
    }

    public Executor getDiskIOExecutor() { return mDiskIOExecutor; }
}
