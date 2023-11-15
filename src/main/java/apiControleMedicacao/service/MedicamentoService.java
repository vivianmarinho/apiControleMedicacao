package apiControleMedicacao.service;

import apiControleMedicacao.model.Medicamento;
import apiControleMedicacao.repository.MedicamentoRepository;
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

