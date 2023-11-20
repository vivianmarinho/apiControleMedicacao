package apiControleMedicacao.service;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.Medicamento;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.MedicamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicamentoService {

        @Autowired
        private MedicamentoRepository medicamentoRepository;

        public Medicamento adicionarMedicamento(Medicamento medicamento) {
            return medicamentoRepository.save(medicamento);
        }

        public Medicamento deletarMedicamentoPorId(Long id) {
            // Verifique se o ID não é nulo antes de realizar a exclusão
            if (id == null) {
                throw new IllegalArgumentException("O ID do medicamento não pode ser nulo.");
            }
            Optional<Medicamento> medicamentoOptional = medicamentoRepository.findById(id);

            if (medicamentoOptional.isPresent()) {
                medicamentoRepository.deleteById(id);
            } else {
                throw new EntityNotFoundException("Medicamento não encontrado com o ID: " + id);
            }
            return null;
        }

        public Medicamento buscarMedicamentoPorId(Long id) {
            if (id == null) {
                throw new IllegalArgumentException("O ID do medicamento não pode ser nulo.");
            }

            Optional<Medicamento> optionalMedicamento = medicamentoRepository.findById(id);

            if (optionalMedicamento.isPresent()) {
                return optionalMedicamento.get();
            } else {
                // Lide com o caso em que o Medicamento não foi encontrado, por exemplo, lançando uma exceção ou retornando um valor padrão.
                // Exemplo: throw new MedicamentoNaoEncontradoException("Medicamento não encontrado");
            }


            return null;
        }
    }

