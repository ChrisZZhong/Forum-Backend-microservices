package com.forum.historyservice.service;

import com.forum.historyservice.dao.HistoryDao;
import com.forum.historyservice.domain.History;
import com.forum.historyservice.dto.request.HistoryRequest;
import com.forum.historyservice.security.AuthUserDetail;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    private HistoryDao historyDao;

    @Autowired
    public void HistoryService(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    @Transactional
    public List<History> getAllHistoryByUserId(int userId) { return historyDao.getAllHistoryByUserId(userId); }

    @Transactional
    public History findHistoryByPostIdAndUserId(String postId, int userId) {
        return historyDao.findHistoryByPostIdAndUserId(postId, userId);
    }

    @Transactional
    public void addHistory(History... histories) {
        for (History h : histories) {
            historyDao.addHistory(h);
        }
    }
    @Transactional
    public void updateHistory(History history) {
        historyDao.updateHistory(history);
    }

    @Transactional
    public boolean ifHistoryExisted(String postId, int userId) {
        return historyDao.ifHistoryExisted(postId, userId);
    }

    public void addHistoryToDatabase(HistoryRequest request) {
        History history = History.builder()
                .userId(request.getUserId())
                .postId(request.getPostId())
                .viewDate(LocalDateTime.now().toString())
                .build();
        addHistory(history);
    }

    public void updateHistoryInDatabase(String postId, int userId) {
        History history = findHistoryByPostIdAndUserId(postId, userId);
        history.setViewDate(LocalDateTime.now().toString());
        updateHistory(history);
    }
    @Transactional
    public void addOrUpdateHistory(HistoryRequest request) {
        String postId = request.getPostId();
        int userId = request.getUserId();
        boolean historyExisted = ifHistoryExisted(postId, userId);
        if (historyExisted) {
            updateHistoryInDatabase(postId, userId);
        } else {
            addHistoryToDatabase(request);
        }
    }
}
