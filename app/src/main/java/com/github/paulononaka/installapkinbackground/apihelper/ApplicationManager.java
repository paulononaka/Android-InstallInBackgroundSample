package com.github.paulononaka.installapkinbackground.apihelper;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;

public class ApplicationManager {

	public final int INSTALL_REPLACE_EXISTING = 2;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} on success.
     * @hide
     */
    public static final int INSTALL_SUCCEEDED = 1;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if the package is
     * already installed.
     * @hide
     */
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if the package archive
     * file is invalid.
     * @hide
     */
    public static final int INSTALL_FAILED_INVALID_APK = -2;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if the URI passed in
     * is invalid.
     * @hide
     */
    public static final int INSTALL_FAILED_INVALID_URI = -3;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if the package manager
     * service found that the device didn't have enough storage space to install the app.
     * @hide
     */
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if a
     * package is already installed with the same name.
     * @hide
     */
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the requested shared user does not exist.
     * @hide
     */
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * a previously installed package of the same name has a different signature
     * than the new package (and the old package's data was not removed).
     * @hide
     */
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package is requested a shared user which is already installed on the
     * device and does not have matching signature.
     * @hide
     */
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package uses a shared library that is not available.
     * @hide
     */
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package uses a shared library that is not available.
     * @hide
     */
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package failed while optimizing and validating its dex files,
     * either because there was not enough storage or the validation failed.
     * @hide
     */
    public static final int INSTALL_FAILED_DEXOPT = -11;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package failed because the current SDK version is older than
     * that required by the package.
     * @hide
     */
    public static final int INSTALL_FAILED_OLDER_SDK = -12;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package failed because it contains a content provider with the
     * same authority as a provider already installed in the system.
     * @hide
     */
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package failed because the current SDK version is newer than
     * that required by the package.
     * @hide
     */
    public static final int INSTALL_FAILED_NEWER_SDK = -14;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package failed because it has specified that it is a test-only
     * package and the caller has not supplied the {@link #INSTALL_ALLOW_TEST}
     * flag.
     * @hide
     */
    public static final int INSTALL_FAILED_TEST_ONLY = -15;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the package being installed contains native code, but none that is
     * compatible with the the device's CPU_ABI.
     * @hide
     */
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package uses a feature that is not available.
     * @hide
     */
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;

    // ------ Errors related to sdcard
    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * a secure container mount point couldn't be accessed on external media.
     * @hide
     */
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package couldn't be installed in the specified install
     * location.
     * @hide
     */
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;

    /**
     * Installation return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)} if
     * the new package couldn't be installed in the specified install
     * location because the media is not available.
     * @hide
     */
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser was given a path that is not a file, or does not end with the expected
     * '.apk' extension.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser was unable to retrieve the AndroidManifest.xml file.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser encountered an unexpected exception.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser did not find any certificates in the .apk.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser found inconsistent certificates on the files in the .apk.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser encountered a CertificateEncodingException in one of the
     * files in the .apk.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser encountered a bad or missing package name in the manifest.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser encountered a bad shared user id name in the manifest.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser encountered some structural problem in the manifest.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;

    /**
     * Installation parse return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the parser did not find any actionable tags (instrumentation or application)
     * in the manifest.
     * @hide
     */
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;

    /**
     * Installation failed return code: this is passed to the {@link IPackageInstallObserver} by
     * {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}
     * if the system failed to install the package because of system issues.
     * @hide
     */
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;

	private PackageInstallObserver observer;
	private PackageManager pm;
	private Method method;

	private OnInstalledPackaged onInstalledPackaged;

    class PackageInstallObserver extends IPackageInstallObserver.Stub {

		public void packageInstalled(String packageName, int returnCode) throws RemoteException {
			if (onInstalledPackaged != null) {
				onInstalledPackaged.packageInstalled(packageName, returnCode);
			}
		}
	}

	public ApplicationManager(Context context) throws SecurityException, NoSuchMethodException {

        observer = new PackageInstallObserver();
        pm = context.getPackageManager();

        Class<?>[] types = new Class[] {Uri.class, IPackageInstallObserver.class, int.class, String.class};
		method = pm.getClass().getMethod("installPackage", types);
	}

	public void setOnInstalledPackaged(OnInstalledPackaged onInstalledPackaged) {
		this.onInstalledPackaged = onInstalledPackaged;
	}

	public void installPackage(String apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		installPackage(new File(apkFile));
	}

	public void installPackage(File apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (!apkFile.exists()) throw new IllegalArgumentException();
		Uri packageURI = Uri.fromFile(apkFile);
		installPackage(packageURI);
	}

	public void installPackage(Uri apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		method.invoke(pm, new Object[] {apkFile, observer, INSTALL_REPLACE_EXISTING, null});
	}

}
