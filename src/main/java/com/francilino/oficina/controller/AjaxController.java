package com.francilino.oficina.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjaxController {
/*
    @Autowired
    private ConjugeRepository conjugeRepository;
    @Autowired
    private FilhoRepository filhoRepository;
    @Autowired
    private FormacaoRepository formacaoRepository;
    @Autowired
    private ProfissaoRepository profissaoRepository;
    @Autowired
    private ComoChegouRepository comoChegouRepository;
    @Autowired
    private IgrejaRepository igrejaRepository;
    @Autowired
    private OutraIgrejaRepository outraIgrejaRepository;
    @Autowired
    private OutraReligiaoRepository outraReligiaoRepository;
    @Autowired
    private InstrumentoRepository instrumentoRepository;
    @Autowired
    private GrupoFamiliarRepository grupoFamiliarRepository;
    @Autowired
    private MinisterioRepository ministerioRepository;

    @RequestMapping(value = "buscaConjuge", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaConjuge() {
        Gson gson = new Gson();
        return gson.toJson(conjugeRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaFilho", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaFilho() {
        Gson gson = new Gson();
        return gson.toJson(filhoRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaTodosFormacao", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaTodosFormacao() {
        Gson gson = new Gson();
        return gson.toJson(formacaoRepository.findAllByOrderByCursoAsc());
    }

    @RequestMapping(value = "buscaFormacao", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaFormacao() {
        Gson gson = new Gson();
        return gson.toJson(formacaoRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaProfissao", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaProfissao() {
        Gson gson = new Gson();
        return gson.toJson(profissaoRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaComoChegou", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaComoChegou() {
        Gson gson = new Gson();
        return gson.toJson(comoChegouRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaIgreja", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaIgreja() {
        Gson gson = new Gson();
        return gson.toJson(igrejaRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaOutraIgreja", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaOutraIgreja(Integer igreja) {
        if (igreja == null) {
            Gson gson = new Gson();
            return gson.toJson(outraIgrejaRepository.findTopByOrderByIdDesc());
        } else {
            Gson gson = new Gson();
            return gson.toJson(outraIgrejaRepository.findByIgrejaOrderByBairroAsc(igrejaRepository.findById(igreja)));
        }
    }

    @RequestMapping(value = "buscaOutraReligiao", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaOutraReligiao() {
        Gson gson = new Gson();
        return gson.toJson(outraReligiaoRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaInstrumento", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaInstrumento() {
        Gson gson = new Gson();
        return gson.toJson(instrumentoRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaGrupoFamiliar", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaGrupoFamiliar() {
        Gson gson = new Gson();
        return gson.toJson(grupoFamiliarRepository.findTopByOrderByIdDesc());
    }

    @RequestMapping(value = "buscaMinisterio", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String buscaMinisterio() {
        Gson gson = new Gson();
        return gson.toJson(ministerioRepository.findTopByOrderByIdDesc());
    }
*/}
