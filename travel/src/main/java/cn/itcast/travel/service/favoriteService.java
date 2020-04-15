package cn.itcast.travel.service;

public interface favoriteService {
    Boolean findFavoriteByRidAndUid(int rid,int uid);

    void addFavorite(int rid, int uid);
}
