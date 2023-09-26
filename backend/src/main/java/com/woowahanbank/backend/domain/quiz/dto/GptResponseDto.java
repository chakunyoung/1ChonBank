package com.woowahanbank.backend.domain.quiz.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GptResponseDto {

	private String id;
	private String object;
	private Long created;
	private String model;
	private List<Choice> choices;

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Choice {
		private int index;
		private Message message;

	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Message {
		private String role;
		private String content;
	}


}
