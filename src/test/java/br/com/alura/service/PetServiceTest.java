package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Pet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PetServiceTest {

    private final ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);
    private final PetService petService = new PetService(client);
    private final HttpResponse<String> response = mock(HttpResponse.class);

    @Test
    void deveVerificarSeDispararRequisicaoPostSeraChamado() throws IOException, InterruptedException {

        String userInput = String.format(
                "meuAbrigo%s" +
                "pets.csv%s",
                System.lineSeparator(),
                System.lineSeparator());

        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        when(client.dispararRequisicaoPost(anyString(), any(Pet.class)))
                .thenReturn(response);

        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("");

        petService.importarPetsDoAbrigo();

        verify(client, atLeastOnce())
                .dispararRequisicaoPost(anyString(), any(Pet.class));
    }
}