package com.forum.historyservice.dao;

import com.forum.historyservice.domain.History;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
@Repository
public class HistoryDao extends AbstractHibernateDao<History> {
    public HistoryDao() { setClazz(History.class);}

    public List<History> getAllHistoryByUserId(Integer userId) {
        Session session = getCurrentSession();
        Query<History> query = session.createQuery("FROM History WHERE userId = :userId ORDER BY viewDate DESC", History.class);
        query.setParameter("userId", userId);
        return query.list();
    }

    public void addHistory(History history) {
        add((History) history);
    }

    public void updateHistory(History history) {
        update((History) history);
    }

    public History findHistoryByPostIdAndUserId(String postId, int userId) {
        Session session = getCurrentSession();
        Query<History> query = session.createNativeQuery("SELECT * FROM History WHERE post_id = :postId AND user_id = :userId", History.class);
        query.setParameter("postId", postId);
        query.setParameter("userId", userId);
        // Use Query.uniqueResult() instead of Query.list() since there's only one result expected.
        return query.uniqueResult();
    }

    public boolean ifHistoryExisted(String postId, int userId) {
        Session session = getCurrentSession();
        Query<Long> query = session.createQuery(
                "SELECT COUNT(*) FROM History WHERE postId = :postId AND userId = :userId",
                Long.class
        );
        query.setParameter("postId", postId);
        query.setParameter("userId", userId);
        Long count = query.getSingleResult();
        return count > 0;
    }
}
