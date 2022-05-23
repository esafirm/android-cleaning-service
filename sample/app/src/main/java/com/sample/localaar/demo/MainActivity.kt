package com.sample.localaar.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.localaar.demo.R.string.used_full_qualified_import
import com.sample.localaar.myawesomemodule1.AwesomeModule1
import com.sample.localaar.myawesomemodule2.AwesomeModule2
import com.sample.localaar.myawesomemodule3.AwesomeModule3
import kotlinx.android.synthetic.main.activity_main.*

// Import alias in Kotlin
import com.sample.localaar.myawesomemodule1.R.string as RString

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView2.text = AwesomeModule1.greet
        textView3.text = AwesomeModule2.greet
        textView4.text = AwesomeModule3.greet

        // Unicode
        textView4.text = getString(R.string.used_unicode_string, "man")

        // Fully qualified import
        textView4.text = getString(used_full_qualified_import)

        // This is alias
        textView4.text = getString(RString.used_aliased)

        setContentView(R.layout.activity_one)
    }
}
