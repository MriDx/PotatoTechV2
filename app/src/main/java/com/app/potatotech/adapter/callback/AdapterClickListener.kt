package com.app.potatotech.adapter.callback

interface AdapterClickListener {

    fun onClicked(type: AdapterType, data: Any)

    fun onLongPressed(type: AdapterType, data: Any)

}