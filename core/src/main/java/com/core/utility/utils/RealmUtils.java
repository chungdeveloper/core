package com.core.utility.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Le Duc Chung on 2017-10-25.
 * on device 'shoot'
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class RealmUtils {
    public static final String ID = "id";
    @SuppressLint("StaticFieldLeak")
    private static RealmUtils sRealmUtils;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private Realm realm;
    private AppLog log;

    public static synchronized RealmUtils getInstance() {
        return sRealmUtils == null ? sRealmUtils = newInstance(context) : sRealmUtils;
    }

    public static RealmUtils newInstance(Context context) {
        return new RealmUtils(context);
    }

    public static boolean invalidateObject(RealmObject object) {
        return !object.isValid();
    }

    public RealmUtils(Context context) {
        RealmUtils.context = context;
        log = AppLog.newInstance(RealmUtils.class);
        initRealm(context);
    }

    public static void initRealmUtils(Context context) {
        sRealmUtils = new RealmUtils(context);
    }

    private void initRealm(Context context) {
        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(configuration);
        log.d("Realm created");
    }

    public synchronized Realm getRealm() {
        return realm;
    }

    public <T extends RealmObject> RealmQuery<T> where(Class<T> classObject) {
        return realm.where(classObject);
    }

    public synchronized void deleteRealmObject(final RealmObject object) {
        if (object == null) {
            log.d("Object not found");
            return;
        }
        if (invalidateObject(object)) {
            log.d(object.getClass().getSimpleName() + " not validate to delete");
            return;
        }
        try {
            realm.beginTransaction();
            object.deleteFromRealm();
            realm.commitTransaction();
            log.w(object.getClass().getSimpleName() + " deleted");
        } catch (Exception ex) {
            log.d(ex.toString());
        }
    }

    public synchronized void clearTable(Class<? extends RealmObject> classData) {
        realm.beginTransaction();
        realm.delete(classData);
        realm.commitTransaction();
    }

    public synchronized <T extends RealmObject> void copyToRealmOrUpdateList(final List<T> objects) {
        if (objects == null) return;
        for (T entry : objects) {
            copyToRealmOrUpdate(entry);
        }
    }

    public synchronized void copyToRealmOrUpdate(final RealmObject object) {
        if (invalidateObject(object)) {
            log.d(object.getClass().getSimpleName() + " not validate to copy or update");
            return;
        }
        realm.executeTransaction(realm -> {
            try {
                realm.copyToRealmOrUpdate(object);
                log.d(object.getClass().getSimpleName() + " copy or update successful");
            } catch (Exception ex) {
                log.d(ex.toString());
            }
        });
    }


    public synchronized void copyToRealm(final RealmObject object) {
        if (invalidateObject(object)) {
            log.d(object.getClass().getSimpleName() + " not validate to copy");
            return;
        }
        realm.executeTransaction(realm -> {
            try {
                realm.copyFromRealm(object);
                log.d(object.getClass().getSimpleName() + " copy successful");
            } catch (Exception ex) {
                log.d(ex.toString());
            }
        });
    }

    public <T extends RealmObject> int genRealmID(final Class<T> aClass) {
        Integer id = getMaxValue(aClass, ID);
        return id == null ? 1 : (id + 1);
    }

    public <T extends RealmObject> Integer getMaxValue(Class<T> tClass, String key) {
        Number currentIdNum = realm.where(tClass).max(key);
        return currentIdNum == null ? null : currentIdNum.intValue();
    }

    public <T extends RealmObject> T getLastObject(Class<T> aClass) {
        Integer id = getMaxValue(aClass, ID);
        if (id == null) {
            return null;
        }

        return where(aClass).equalTo(ID, id).findFirst();
    }

    public <T extends RealmObject> boolean exist(String field, String id, Class<T> tClass) {
        RealmResults<T> results = where(tClass).equalTo(field, id).findAll();
        return results != null && results.size() > 0;
    }

    public <T extends RealmObject> T getLastObject(String id, Class<T> tClass) {
        if (id == null) return null;
        RealmResults<T> results = where(tClass).contains(ID, id).findAll();
        if (results.isEmpty()) return null;
        return results.last();
    }

    public void closeRealm() {
        if (realm == null) return;
        realm.close();
        log.w("realm closed");
    }

    public void release() {
        realm.executeTransaction(realm -> realm.deleteAll());
        closeRealm();
        sRealmUtils = null;
    }
}
