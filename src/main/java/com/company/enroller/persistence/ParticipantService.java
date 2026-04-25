package com.company.enroller.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

	static DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

    public Collection<Participant> getAll(String key, String sortMode, String sortOrder) {
        String hql = "FROM Participant WHERE login LIKE :login";

        if ("login".equals(sortMode)) {
            hql += " ORDER BY login";

            if ("DESC".equals(sortOrder)) {
                hql += " DESC";
            } else {
                hql += " ASC";
            }
        }

        Query query = connector.getSession().createQuery(hql);
        query.setParameter("login", "%" + key + "%");

        return query.list();
    }

    public Participant findByLogin(String login){
        return (Participant) connector.getSession().get(Participant.class, login);
    }

    public void add (Participant participant){
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(participant);
        transaction.commit();
    }

    public void delete(Participant participant){
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(participant);
        transaction.commit();
    }

    public void update(Participant participant){
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().update(participant);
        transaction.commit();
    }

}
