package com.medcare.service.impl;

import com.medcare.repository.ConsultationRepository;
import com.medcare.repository.FactureRepository;
import com.medcare.repository.MedicamentRepository;
import com.medcare.repository.PatientRepository;
import com.medcare.repository.RendezVousRepository;
import com.medcare.repository.UserRepository;
import com.medcare.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final PatientRepository patientRepository;
    private final ConsultationRepository consultationRepository;
    private final RendezVousRepository rendezVousRepository;
    private final MedicamentRepository medicamentRepository;
    private final FactureRepository factureRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(PatientRepository patientRepository,
                                 ConsultationRepository consultationRepository,
                                 RendezVousRepository rendezVousRepository,
                                 MedicamentRepository medicamentRepository,
                                 FactureRepository factureRepository,
                                 UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.consultationRepository = consultationRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.medicamentRepository = medicamentRepository;
        this.factureRepository = factureRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("nombrePatients", patientRepository.count());
        stats.put("nombreConsultations", consultationRepository.count());
        stats.put("nombreRendezVous", rendezVousRepository.count());
        stats.put("nombreMedicaments", medicamentRepository.count());
        stats.put("nombreFactures", factureRepository.count());
        stats.put("nombreUtilisateurs", userRepository.count());
        return stats;
    }
}
