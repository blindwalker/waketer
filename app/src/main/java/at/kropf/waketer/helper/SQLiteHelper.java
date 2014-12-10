package at.kropf.waketer.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import at.kropf.waketer.model.Artist;

/**
 * Created by martinkropf on 13.10.14.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TABLE_ARTIST = "artist";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATEADDED = "date_added";
    private static final String KEY_EXTERNALID = "external_id";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_DESCRIPTION,KEY_DATEADDED, KEY_EXTERNALID};

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WaketerDB";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_ARTIST_TABLE = "CREATE TABLE artist ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "description TEXT "+
                "date_added TEXT "+
                "external_id INTEGER)";

        // create books table
        db.execSQL(CREATE_ARTIST_TABLE);
    }

    public void addArtist(Artist artist){
        //for logging
        Log.d("addArtist", artist.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, artist.getId());
        values.put(KEY_NAME, artist.getName());
        values.put(KEY_DESCRIPTION, artist.getDescription());
        values.put(KEY_DATEADDED, artist.getDateAdded());
        values.put(KEY_EXTERNALID, artist.getMbid());

        // 3. insert
        db.insert(TABLE_ARTIST, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Artist getBook(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_ARTIST, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Artist artist = new Artist();
        artist.setId(Integer.parseInt(cursor.getString(0)));
        artist.setName(cursor.getString(1));
        artist.setDescription(cursor.getString(2));
        artist.setDateAdded(cursor.getString(3));
        artist.setMbid(cursor.getString(4));

        //log
        Log.d("getArtist("+id+")", artist.toString());

        // 5. return book
        return artist;
    }

    public List<Artist> getAllArtists() {
        List<Artist> artists = new LinkedList<Artist>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ARTIST;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Artist artist = null;
        if (cursor.moveToFirst()) {
            do {
                artist = new Artist();
                artist.setId(Integer.parseInt(cursor.getString(0)));
                artist.setName(cursor.getString(1));
                artist.setDescription(cursor.getString(2));
                artist.setDateAdded(cursor.getString(3));
                artist.setMbid(cursor.getString(4));

                // Add book to books
                artists.add(artist);
            } while (cursor.moveToNext());
        }

        Log.d("getAllArtists()", artist.toString());

        return artists;
    }

    public void deleteBook(Artist artist) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_ARTIST, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(artist.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleted", artist.toString());

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS artist");

        // create fresh books table
        this.onCreate(db);
    }

}