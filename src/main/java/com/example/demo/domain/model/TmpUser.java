package com.example.demo.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tmp_user")
public class TmpUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "mail_address")
	private String mailAddress;

	@Column(name = "password")
	private String password;

	@Column(name = "uuid")
	private String uuid;

	public TmpUser(String mailAddress, String password, String uuid) {
		this.mailAddress = mailAddress;
		this.password = password;
		this.uuid = uuid;
	}
}
