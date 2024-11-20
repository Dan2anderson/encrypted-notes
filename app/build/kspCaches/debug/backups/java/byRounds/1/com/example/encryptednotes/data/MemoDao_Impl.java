package com.example.encryptednotes.data;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MemoDao_Impl implements MemoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MemoModel> __insertionAdapterOfMemoModel;

  private final EntityDeletionOrUpdateAdapter<MemoModel> __deletionAdapterOfMemoModel;

  public MemoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMemoModel = new EntityInsertionAdapter<MemoModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `memos_table` (`id`,`title`,`subTitle`,`memo`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MemoModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getSubTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSubTitle());
        }
        if (value.getMemo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMemo());
        }
      }
    };
    this.__deletionAdapterOfMemoModel = new EntityDeletionOrUpdateAdapter<MemoModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `memos_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MemoModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public Object insertMemo(final MemoModel memo, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMemoModel.insert(memo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertAllMemos(final List<MemoModel> memos,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMemoModel.insert(memos);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteMemo(final MemoModel memo, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMemoModel.handle(memo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<MemoModel>> getAllMemos() {
    final String _sql = "SELECT * FROM memos_table ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"memos_table"}, false, new Callable<List<MemoModel>>() {
      @Override
      public List<MemoModel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subTitle");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final List<MemoModel> _result = new ArrayList<MemoModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MemoModel _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSubTitle;
            if (_cursor.isNull(_cursorIndexOfSubTitle)) {
              _tmpSubTitle = null;
            } else {
              _tmpSubTitle = _cursor.getString(_cursorIndexOfSubTitle);
            }
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            _item = new MemoModel(_tmpId,_tmpTitle,_tmpSubTitle,_tmpMemo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MemoModel>> searchMemos(final String query) {
    final String _sql = "SELECT * FROM memos_table WHERE title LIKE ? OR subTitle LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"memos_table"}, false, new Callable<List<MemoModel>>() {
      @Override
      public List<MemoModel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subTitle");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final List<MemoModel> _result = new ArrayList<MemoModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MemoModel _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSubTitle;
            if (_cursor.isNull(_cursorIndexOfSubTitle)) {
              _tmpSubTitle = null;
            } else {
              _tmpSubTitle = _cursor.getString(_cursorIndexOfSubTitle);
            }
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            _item = new MemoModel(_tmpId,_tmpTitle,_tmpSubTitle,_tmpMemo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
