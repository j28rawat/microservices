package com.jitendra.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity(name = "person")
@Table(name = "person")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NonNull
	@Column(unique = true)
	private String uid;

	@NonNull
	@Column(name = "firstname")
	private String firstName;

	@NonNull
	@Column(name = "lastname")
	private String lastName;

	@NonNull
	@Column
	private Integer age;

}
