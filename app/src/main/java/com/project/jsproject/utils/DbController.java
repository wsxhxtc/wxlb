package com.project.jsproject.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.project.jsproject.entity.ActionEntity;
import com.project.jsproject.entity.ActionEntityDao;
import com.project.jsproject.entity.AllPlanEntity;
import com.project.jsproject.entity.AllPlanEntityDao;
import com.project.jsproject.entity.DaoMaster;
import com.project.jsproject.entity.DaoSession;
import com.project.jsproject.entity.PlanEntity;
import com.project.jsproject.entity.PlanEntityDao;
import com.project.jsproject.entity.ResultEntity;
import com.project.jsproject.entity.ResultEntityDao;

import java.util.List;

public class DbController {
    private final AllPlanEntityDao allactionEntityDao;
    private final PlanEntityDao planactionEntityDao;
    private final ResultEntityDao resultEntityDao;
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */

    private ActionEntityDao actionEntityDao;
    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbController.class) {
                if (mDbController == null) {
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "person1.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();

        actionEntityDao = mDaoSession.getActionEntityDao();
        allactionEntityDao = mDaoSession.getAllPlanEntityDao();
        planactionEntityDao = mDaoSession.getPlanEntityDao();
        resultEntityDao = mDaoSession.getResultEntityDao();

    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     *
     * @param UrlEntity
     */
    public void insertOrReplace(ActionEntity UrlEntity) {
        actionEntityDao.insertOrReplace(UrlEntity);
    }


    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param
     */
    public long insert(ActionEntity urlEntity) {
        return actionEntityDao.insert(urlEntity);
    }

    /**
     * 更新数据
     *
     * @param UrlEntity
     */
    public void update(ActionEntity UrlEntity) {
        ActionEntity mOldUrlEntity = actionEntityDao.queryBuilder().where(ActionEntityDao.Properties.Id.eq(UrlEntity.getId())).build().unique();//拿到之前的记录
        if (mOldUrlEntity != null) {
        /*    mOldUrlEntity.setUrl("");
            urlEntityDao.update(mOldUrlEntity);*/
        }
    }

    /**
     * 按条件查询数据
     */
    public List<ActionEntity> searchByWhere(int wherecluse) {
        List<ActionEntity> UrlEntitys = (List<ActionEntity>) actionEntityDao.queryBuilder().where(ActionEntityDao.Properties.Id.eq(wherecluse)).build().list();
        return UrlEntitys;
    }

    public List<AllPlanEntity> searchAll() {
        List<AllPlanEntity> UrlEntitys = (List<AllPlanEntity>) allactionEntityDao.queryBuilder().list();
        return UrlEntitys;
    }

    public List<PlanEntity> searchPlanAll(String username, Long wherecluse) {
        List<PlanEntity> UrlEntitys = (List<PlanEntity>) planactionEntityDao.queryBuilder().where(
                PlanEntityDao.Properties.Actionid.eq(String.valueOf(wherecluse))
                , PlanEntityDao.Properties.Username.eq(username)).build().list();
        return UrlEntitys;
    }

    public void insertOrReplace(PlanEntity planEntity) {
        planactionEntityDao.insertOrReplace(planEntity);
    }


    /**
     * 查询当前时间是否存在
     */
    public List<ActionEntity> searchByNametime(String username, String wherecluse) {
        List<ActionEntity> UrlEntitys = (List<ActionEntity>) actionEntityDao.queryBuilder().where(
                ActionEntityDao.Properties.Time.eq(wherecluse)
                , ActionEntityDao.Properties.Name.eq(username)).build().list();
        return UrlEntitys;
    }

    /**
     * 查询根据用户名
     *
     * @param username
     * @return
     */
    public List<ActionEntity> searchByName(String username) {
        List<ActionEntity> UrlEntitys = (List<ActionEntity>) actionEntityDao.queryBuilder().where(
                ActionEntityDao.Properties.Name.eq(username)).build().list();
        return UrlEntitys;
    }

    /**
     * 插入结果
     *
     * @param resultEntity
     */
    public void insertOrReplace(ResultEntity resultEntity) {
        resultEntityDao.insertOrReplace(resultEntity);
    }

    /**
     * getList
     *
     * @param username
     * @return
     */

    public List<ResultEntity> searchTimerAll(String username) {
        List<ResultEntity> UrlEntitys = (List<ResultEntity>) resultEntityDao.queryBuilder().where(
                ResultEntityDao.Properties.Name.eq(username)).build().list();
        return UrlEntitys;
    }


}
