package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.favoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.favoriteService;

public class favoriteServiceImpl implements favoriteService {
    private favoriteDao dao = new favoriteDaoImpl();
    @Override
    public Boolean findFavoriteByRidAndUid(int rid, int uid) {
        Favorite favorite = dao.findFavoriteByRidAndUid(rid, uid);
        if (favorite == null){
            return  false;
        }else {
            return true;
        }
    }

    @Override
    public void addFavorite(int rid, int uid) {
        dao.add(rid,uid);
    }
}
