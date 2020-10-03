package com.demo.videochat.app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "FeedsDatabase"
        private val TABLE_CONTACTS = "FeedsTable"
        private val KEY_NAME = "name"
        private val KEY_LIVE = "live"
        private val KEY_CREATED = "created"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_NAME + " TEXT,"
                + KEY_LIVE + " INTEGER,"
                + KEY_CREATED + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

    fun addFeed(feed: FeedItemModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, feed.name)
        contentValues.put(KEY_LIVE, feed.live)
        contentValues.put(KEY_CREATED, feed.created)
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    fun viewFeeds(): List<FeedItemModel> {
        val feedList: ArrayList<FeedItemModel> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var name: String
        var live: Int
        var created: String
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("name"))
                live = cursor.getInt(cursor.getColumnIndex("live"))
                created = cursor.getString(cursor.getColumnIndex("created"))
                val feed = FeedItemModel(name = name, live = live, created = created)
                feedList.add(feed)
            } while (cursor.moveToNext())
        }
        return feedList
    }

    fun updateFeed(feed: FeedItemModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, feed.name)
        contentValues.put(KEY_LIVE, feed.live)
        contentValues.put(KEY_CREATED, feed.created)

        val success = db.update(TABLE_CONTACTS, contentValues, "name=" + feed.name, null)
        db.close()
        return success
    }

    // Suggested update code
    fun updateByName(feed: FeedItemModel): Int {
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, feed.name)
        contentValues.put(KEY_LIVE, feed.live)
        contentValues.put(KEY_CREATED, feed.created)
        val whereClause = "$KEY_NAME=?"
        val whereArgs = arrayOf(feed.name)
        return this.writableDatabase.update(TABLE_CONTACTS, contentValues, whereClause, whereArgs)
    }

    fun deleteFeed(feed: FeedItemModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, feed.name)
        val success = db.delete(TABLE_CONTACTS, "name=" + feed.name, null)
        db.close()
        return success
    }
}