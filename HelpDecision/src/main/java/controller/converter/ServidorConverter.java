package controller.converter;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import model.domain.entidades.Servidor;
import model.domain.servicos.ServidorServico;

@ManagedBean
@RequestScoped
public class ServidorConverter implements Converter{

    private  ServidorServico servidorServico = new ServidorServico();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
            return servidorServico.buscarPorId(Integer.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(String.format("%s Não é um objeto servidor", submittedValue)), e);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Integer) {
            return String.valueOf(modelValue);
        } else {
            throw new ConverterException(new FacesMessage(String.format("%s Não é um inteiro", modelValue)));
        }
    }

}
