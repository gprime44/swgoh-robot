package org.optimus.swgohrobot;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.Assert;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 20)
public class SwgohRobot {

    // Package to measure
    private static final String BASIC_SAMPLE_PACKAGE = "com.ea.game.starwarscapital_row";

    private static final int LAUNCH_TIMEOUT = 10000;

    private static final int X_MAX_PIX = 1776;
    private static final int Y_MAX_PIX = 1080;

    private static final int X_BUY = 1657;
    private static final int Y_BUY = 985;

    final private UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    //@Test
    public void testOsirisLogin() {
        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);

        // Wait for the app to appear and the home page to load
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE)), LAUNCH_TIMEOUT);

        waitms(30000);

        mDevice.click((int) (X_MAX_PIX * 0.4), (int) (Y_MAX_PIX * 0.25));
    }

    @Test
    public void testBuy() {
        while (true) {
            waitms(100);
            mDevice.click(X_BUY, Y_BUY);
        }
    }

    @Test
    public void testSoccer() {
        while (true) {
            waitms(600);
            mDevice.click(1080 / 2, 1776  - 80);
        }
    }

    @NonNull
    private UiObject2 getAndWaitByResource(String resourceName) {
        BySelector searchSelector = By.res(resourceName);
        mDevice.wait(Until.hasObject(searchSelector), LAUNCH_TIMEOUT);
        UiObject2 searchChrome = mDevice.findObject(searchSelector);
        Assert.assertNotNull(searchChrome);
        return searchChrome;
    }

    private UiObject2 getAndWaitByText(String resourceName) {
        BySelector searchSelector = By.textContains(resourceName);
        mDevice.wait(Until.hasObject(searchSelector), LAUNCH_TIMEOUT);
        UiObject2 searchChrome = mDevice.findObject(searchSelector);
        Assert.assertNotNull(searchChrome);
        return searchChrome;
    }

    private void waitms(int time) {
        try {
            synchronized (mDevice) {
                mDevice.wait(time);
            }
        } catch (InterruptedException e) { /** Ignore exception **/}
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
