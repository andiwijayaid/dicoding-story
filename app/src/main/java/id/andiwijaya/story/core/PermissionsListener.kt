package id.andiwijaya.story.core

interface PermissionsListener {
    fun onPermissionsGranted()
    fun onPermissionsDenied()
}