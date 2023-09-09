package com.example.pictgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pictgram.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

	Iterable<Topic> findAllByOrderByUpdatedAtDesc();
}