package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface favoriteDao {
    public Favorite findFavoriteByRidAndUid(int rid, int uid);

    int findFavoriteCount(int rid);

    void add(int rid, int uid);
}
