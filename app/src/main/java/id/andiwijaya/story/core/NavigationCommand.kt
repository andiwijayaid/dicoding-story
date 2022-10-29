package id.andiwijaya.story.core

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    class GoTo(val directions: NavDirections): NavigationCommand()
    class Pop(val directions: NavDirections): NavigationCommand()
}