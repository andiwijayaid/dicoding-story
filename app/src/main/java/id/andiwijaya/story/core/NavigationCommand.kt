package id.andiwijaya.story.core

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class GoTo(val directions: NavDirections): NavigationCommand()
    data class Pop(val directions: NavDirections): NavigationCommand()
}