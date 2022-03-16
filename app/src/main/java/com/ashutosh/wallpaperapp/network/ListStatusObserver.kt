package com.ashutosh.wallpaperapp.network

enum class ListStatus{
    INITIAL_LOADING,
    LOADING,
    INSERTED,
    REMOVED,
    UPDATE,
    ERROR
}

class ListStatusObserver(val status: ListStatus,val  positionStart:Int = -1,val  itemCount:Int = -1)