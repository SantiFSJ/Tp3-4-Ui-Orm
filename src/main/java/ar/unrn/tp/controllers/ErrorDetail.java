package ar.unrn.tp.controllers;

import java.time.LocalDateTime;

public record ErrorDetail(LocalDateTime timestamp, String message) {

}