package com.pengin.poinsetia.konkatsudiary.Model;

import io.realm.Realm;
import io.realm.RealmResults;

public class PersonRealmHelper extends AbstractPersonRealmHelper<Person> {

    public static void insertOneShot(Person person) {
        executeTransactionOneShot(insertTransaction(person));
    }

    private static Realm.Transaction insertTransaction(final Person itemRealmObject) {
        return realm -> realm.insertOrUpdate(itemRealmObject);
    }

    @Override
    public void update(Person person) {
        // レコードの追加
        executeTransaction(realm -> realm.copyToRealmOrUpdate(person));
    }

    @Override
    public void delete(int position) {
        // 指定した位置のレコード削除
        RealmResults results = findAll();
        executeTransaction(realm -> results.deleteFromRealm(position));
    }

    @Override
    public void setIndex(Person person, int index) {
        // リスト位置の再設定
        executeTransaction(realm -> person.setIndex(index));
    }

    @Override
    public RealmResults<Person> deleteUnderList(int index) {
        // 削除されたindexよりも下の位置のリストを取ってくる
        return mRealm.where(Person.class)
                     .greaterThan("index",index)
                     .findAllSorted("index");
    }

    @Override
    public Person getRealmObject(int index) {
        // 指定した位置のPersonを返却する
        return mRealm.where(Person.class).equalTo("index",index).findFirst();
    }

    @Override
    public RealmResults<Person> findAll() {
        return mRealm.where(Person.class).findAllSorted("index");
    }

    @Override
    public RealmResults<Person> sortedId() {
        return mRealm.where(Person.class).findAllSorted("id");
    }


}
