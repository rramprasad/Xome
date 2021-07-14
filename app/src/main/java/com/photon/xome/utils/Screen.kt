/*
 * Created by Ramprasad Ranganathan on 29/06/21, 2:44 PM
 * Copyright (c) 2021. All rights reserved
 * Last modified 29/06/21, 2:02 PM
 */

package com.photon.xome.utils

sealed class Screen(val route:String) {
    object HomeScreen : Screen("HomeScreen")
}