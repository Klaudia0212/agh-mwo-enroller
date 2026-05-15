package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll(String key, String sortMode, String sortOrder) {
		String hql = "FROM Meeting WHERE title LIKE :title";

        if ("title".equals(sortMode)) {
            hql += " ORDER BY title";

            if ("DESC".equals(sortOrder)) {
                hql += " DESC";
            } else {
                hql += " ASC";
            }
        }
        Query query = connector.getSession().createQuery(hql);
        query.setParameter("title", "%" + key + "%");
        return query.list();
	}

    public Meeting findById(long id){
        return (Meeting) connector.getSession().get(Meeting.class, id);
    }

    public void add (Meeting meeting){
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        transaction.commit();
    }

    public void delete(Meeting meeting) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(meeting);
        transaction.commit();
    }

    public void update(Meeting meeting){
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().update(meeting);
        transaction.commit();
    }

}
