package ar.unrn.tp.modelo;

import org.springframework.stereotype.Service;

@Service
public class ServicioTarjetaDeCredito implements ServicioValidadorDeTarjetas{
    @Override
    public boolean validar(TarjetaDeCredito tarjeta) {
        return true;
    }
}
