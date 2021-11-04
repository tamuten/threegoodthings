package com.example.demo.domain.model;

import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Good {
	private String good1;
	private String good2;
	private String good3;
	private Date date;
}
