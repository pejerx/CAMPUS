package cit.edu.garol.campus.core.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")

    object UserDashboard : Screen("user_dashboard")

    object AdminDashboard : Screen("admin_dashboard")

    object ReviewReportedItems : Screen("review_reported_items")

}