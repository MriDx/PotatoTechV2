package com.app.potatotech.ui.activity.post_ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.app.potatotech.R
import com.app.potatotech.data.PostData
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.post_ui.*
import kotlin.math.abs

class PostUI : AppCompatActivity() {

    lateinit var postData: PostData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_ui)

        /*setSupportActionBar(toolbar)
        supportActionBar?.apply {
            this.title = ""
            this.setDisplayHomeAsUpEnabled(true)
        }*/

        postData = intent.getSerializableExtra("DATA") as PostData

        postTitleView?.text = postData.title



        postWebView?.apply {
            this.settings.apply {
                this.javaScriptEnabled = true
                this.domStorageEnabled = true
                this.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                this.userAgentString =
                    "Mozilla/5.0 (Linux; Android 9.0.0; Google Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Mobile Safari/537.36"
                this.useWideViewPort = true
            }
            this.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }
            }
        }.also {
            //it?.loadData(generatePost(postData.content), "text/html", "utf-8")
            it?.loadDataWithBaseURL(
                null,
                generatePost(postData.content),
                "text/html",
                "utf-8",
                null
            )
        }

        //Log.d("mridx", "onCreate: ${generatePost(postData.content)}")


        Glide.with(this).asBitmap().load(postData.imageList[1]).into(postImageView)
/*
        app_bar?.addOnOffsetChangedListener { appBarLayout1, i ->
            if (Math.abs(i) === appBarLayout1.getTotalScrollRange()) {
                titleView.setVisibility(View.GONE)
                topView.setVisibility(View.VISIBLE)
            } else if (i === 0) {
                // Expanded
                //titleView.setVisibility(View.VISIBLE);
            } else {
                val h: Int = Math.abs(i)
                val k: Int = titleView.getMeasuredHeight()
                if (h > k) {
                    titleView.setVisibility(View.VISIBLE)
                    topView.setVisibility(View.GONE)
                }
            }
        }*/

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                topView?.visibility = View.VISIBLE
            } else {
                topView?.visibility = View.GONE
            }
        })


    }

    private fun generatePost(content: String): String {
        return "<http>" +
                "<head>" +
                "<link href='https://fonts.gstatic.com' crossorigin rel='preconnect' />" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "<link rel='stylesheet' id='generate-fonts-css' href='//fonts.googleapis.com/css?family=Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic|Lato:100,100italic,300,300italic,regular,italic,700,700italic,900,900italic|Playfair+Display:regular,italic,700,700italic,900,900italic' media='all' />\n" +
                "<link rel='stylesheet' id='wp-block-library-css' href='https://potatotech.in/wp-includes/css/dist/block-library/style.min.css?ver=5.4.4' media='all' />\n" +
                "<link rel='stylesheet' id='ctf_styles-css' href='https://potatotech.in/wp-content/plugins/custom-twitter-feeds/css/ctf-styles.min.css?ver=1.5.1' media='all' />\n" +
                "<link rel='stylesheet' id='wp-show-posts-css' href='https://potatotech.in/wp-content/plugins/wp-show-posts/css/wp-show-posts-min.css?ver=1.1.3' media='all' />\n" +
                "<link rel='stylesheet' id='generate-style-css' href='https://potatotech.in/wp-content/themes/generatepress/assets/css/all.min.css?ver=3.0.2' media='all' />" +
                "<link rel='stylesheet' id='generate-font-icons-css' href='https://potatotech.in/wp-content/themes/generatepress/assets/css/components/font-icons.min.css?ver=3.0.2' media='all' />\n" +
                "<link rel='stylesheet' id='addtoany-css' href='https://potatotech.in/wp-content/plugins/add-to-any/addtoany.min.css?ver=1.15' media='all' />\n" +
                "<link rel='stylesheet' id='generate-blog-css' href='https://potatotech.in/wp-content/plugins/gp-premium/blog/functions/css/style-min.css?ver=1.10.0' media='all' />" +
                "</head>" +
                "<style type='text/css'>" +
                "body {" +
                "margin: 1em;" +
                "background: ${getString(R.string.postBg)}" +
                "}" +
                "p, a, h4, h3, h2, h1 {" +
                "color: " + getString(R.string.postTextColor) +
                "}" +
                "</style>" +
                "<body>" +
                "<div class=\"entry-content\" style=>" +
                "$content" +
                "</div>" +
                "</body>" +
                "</html>"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.share_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    fun share(view: View?) {
        ShareCompat.IntentBuilder.from(this).apply {
            this.setType("text/plain")
            this.setText(postData.link)
        }.also { share ->
            share.startChooser()
        }
    }

    fun back(view: View) = onBackPressed()


}