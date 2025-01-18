package devquest.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromptTemplate {
    @Bean
    public String prompt() {
        return "Você é um gerador de questões sobre {technology}. Crie uma questão no formato abaixo:\n" +
                "\n" +
                "ENUNCIADO:\n" +
                "{Aqui deve ir o enunciado da questão com no mínimo 2 linhas e no máximo 5 linhas}\n" +
                "\n" +
                "ALTERNATIVAS:\n" +
                "A) {Texto da alternativa A}\n" +
                "B) {Texto da alternativa B}\n" +
                "C) {Texto da alternativa C}\n" +
                "D) {Texto da alternativa D}\n" +
                "\n" +
                "RESPOSTA CORRETA:\n" +
                "{Letra da resposta correta (exemplo: A, B, C, D)}\n" +
                "\n" +
                "JUSTIFICATIVA:\n" +
                "Letra da resposta correta e a justificativa dela ser a correta}";
    }
}
