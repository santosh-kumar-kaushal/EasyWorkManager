package com.sample.easyworkmanager

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    val viewModel by viewModels<ActivityViewModel> {
        ActivityViewModel.Factory()
    }

}