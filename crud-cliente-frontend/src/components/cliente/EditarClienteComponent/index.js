import React, { Component } from 'react';
import axios from 'axios';
import TextField from '@material-ui/core/TextField';
import Select from '@material-ui/core/Select';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import ApiService from '../../../services/ApiService';
import { Grid, MenuItem } from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import NavBar from '../../Navbar';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';
import { CPF } from "gerador-validador-cpf";
import { cpfMask, cepMask, celularMask, phoneMask, removeMask, } from '../../../util/mask';


class EditarClienteComponent extends Component {
  constructor(props) {
    super(props);
    this.onChangeCEP = this.onChangeCEP.bind(this);
    this.applyMaskCPF = this.applyMaskCPF.bind(this);
    this.applyMaskCEP = this.applyMaskCEP.bind(this);
    this.removeMaskTelefone = this.removeMaskTelefone.bind(this);
    this.error = '';
    this.state = {
      id: null,
      nome: '',
      cpf: '',
      cep: '',
      logradouro: '',
      complemento: '',
      bairro: '',
      numero: '',
      cidade: '',
      uf: '',
      telefones: [{ numero: '', tipo: '' }],
      emails: ['']
    };
  }

  componentDidMount() {
    this.carregarUsuario();
    ValidatorForm.addValidationRule('isValidCPF', value => CPF.validate(value));
  }

  applyMaskCPF(e) {
    this.setState({cpf: cpfMask(e.target.value)})
  }

  applyMaskCEP(e) {
    this.setState({cep: cepMask(e.target.value)})
  }

  async carregarUsuario() {
    let id = this.props.match.params.id;
    if (id) {
      try {
        const response = await ApiService.getClientePorId(id);
        const { nome, cpf, cep, logradouro, complemento, bairro, numero, cidade, uf, telefones, emails } = response.data;
        this.setState({ id, nome, cpf: cpfMask(cpf), cep: cepMask(cep), logradouro, complemento, bairro, numero, cidade, uf, telefones: this.applyMaskTelefone(telefones), emails });
      } catch (err) {
        console.log(err);
        this.setState({ error: 'Ocorreu um erro ao carregar o cliente' });
      }
      this.setState({ id });
    }

  }

  handleTelefoneNumeroChange = id => e => {
    const telefones = this.state.telefones.map((telefone, i) => {
        
        if (id !== i) return telefone;
        return { ...telefone, numero: telefone.tipo === 'celular' ? celularMask(e.target.value) : phoneMask(e.target.value) };
    })
    this.setState({ telefones });
  }

  handleTelefoneTipoChange = id => e => {
    const telefones = this.state.telefones.map((telefone, i) => {
        if (id !== i) return telefone;
        return { ...telefone, tipo: e.target.value };
    })
    this.setState({ telefones });
  }

  handleAddTelefone = () => {
    this.setState({ telefones: this.state.telefones.concat([ { numero: "", tipo: "" } ]) });
  }

  handleRemoveTelefone = id => () => {
    const telefones = this.state.telefones.slice();
    telefones.splice(id, 1)
    this.setState({ telefones });
  }

  handleEmailChange = id => e => {
    const emails = this.state.emails.map((email, i) => {
        if (id !== i) return email;
        return e.target.value;
    })
    this.setState({ emails });
  } 

  handleAddEmail = () => {
      this.setState({ emails: this.state.emails.concat([""]) });
  }

  handleRemoveEmail = id => () => {
      const emails = this.state.emails.slice();
      emails.splice(id, 1)
      this.setState({ emails });
  }

  handleSubmit = async e => {
    e.preventDefault();
    const { id, nome, cpf, cep, logradouro, complemento, bairro, numero, cidade, uf, telefones, emails } = this.state;
    if (!nome || !cpf || !cep || !logradouro || !bairro || !numero || !cidade || !uf) {
        this.setState({ error: "Preencha todos os dados para cadastrar um cliente" });
    } else {
      try {
        
        if (id) {
          await ApiService.atualizarCliente(id, { nome, cpf: removeMask(cpf), cep: removeMask(cep), logradouro, complemento, bairro, numero, cidade, uf, telefones: this.removeMaskTelefone(telefones), emails });
        } else {
          await ApiService.cadastrarNovoCliente( { nome, cpf: removeMask(cpf), cep: removeMask(cep), logradouro, complemento, bairro, numero, cidade, uf, telefones: this.removeMaskTelefone(telefones), emails });
        }
          this.props.history.push("/clientes");
        } catch (err) {
            console.log(err);
            this.setState({ error: "Ocorreu um erro ao salvar o cliente" });
        }
    }
  };

  applyMaskTelefone = telefones => {
    return telefones.map(tel => {
      tel.numero = tel.tipo === 'celular' ? celularMask(tel.numero) : phoneMask(tel.numero);
      return tel;
    });
  }

  removeMaskTelefone = telefones => {
    return telefones.map(tel => {
      tel.numero = removeMask(tel.numero);
      return tel;
    });
  }

  onChange = e => this.setState({ [e.target.name]: e.target.value });

  onChangeCEP = async (e) => {
    const cep = e.target.value;
    if (cep && cep.length === 9) {
      try {
        axios.defaults.headers.common = {};
        const response = await axios.get('https://viacep.com.br/ws/'+removeMask(this.state.cep)+'/json/');
        const { logradouro, complemento, bairro, localidade, uf } = response.data;
        this.setState({ logradouro, complemento, bairro, cidade: localidade, uf });
        
        } catch (error) {
          console.log(error);
        }
    }
  }  

  render() {
    return (
      <div>
        <NavBar/> 
        <br />
        <br />
        <Typography variant="h4" style={style}>
          {this.state.id ? "Editar Cliente" : "Novo Cliente"}
        </Typography>
          {this.state.error && <p style={{color: 'red'}}>{this.state.error}</p>}
        <ValidatorForm 
          ref="form"
          onError={errors => console.log(errors)}
          onSubmit={this.handleSubmit}
        >

          <TextValidator
            placeholder="Nome"
            fullWidth
            margin="normal"
            name="nome"
            validators={['required', 'minStringLength:3', 'maxStringLength:100']}
            errorMessages={['O nome é obrigatório', 'O nome deve conter entre 3 e 100 caracteres', 'O nome deve conter entre 3 e 100 caracteres']}
            inputProps={{ minLength: 3, maxLength: 100 }}
            value={this.state.nome}
            onChange={this.onChange}
          />
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>  
              <TextValidator
                placeholder="CPF"
                fullWidth
                margin="normal"
                name="cpf"
                validators={['required', 'isValidCPF']}
                errorMessages={['CPF é obrigatório', 'Informe um CPF válido']}
                inputProps={{ maxLength: 14 }}
                value={this.state.cpf}
                onChange={this.applyMaskCPF}
              />
            </Grid>
            <Grid item xs={12} sm={6}>  
              <TextValidator
                type="text"
                placeholder="CEP"
                fullWidth
                margin="normal"
                name="cep"
                validators={['required',]}
                errorMessages={['CEP é obrigatório']}
                inputProps={{ maxLength: 9 }}
                value={this.state.cep}
                onChange={this.applyMaskCEP}
                onKeyUp={this.onChangeCEP.bind(this)}
              />
            </Grid>
          </Grid>
          <TextField
            placeholder="Logradouro"
            fullWidth
            margin="normal"
            name="logradouro"
            maxLength="100"
            disabled={true}
            value={this.state.logradouro}
            onChange={this.onChange}
          />

          <Grid container spacing={2}>
            <Grid item xs={12} sm={5}> 
            <TextField
                placeholder="Bairro"
                fullWidth
                margin="normal"
                name="bairro"
                maxLength="8"
                disabled={true}
                value={this.state.bairro}
                onChange={this.onChange}
              /> 
            </Grid>
            <Grid item xs={12} sm={5}>  
              <TextField
                placeholder="Cidade"
                fullWidth
                margin="normal"
                name="cidade"
                disabled={true}
                value={this.state.cidade}
                onChange={this.onChange}
              />
            </Grid>
            <Grid item xs={12} sm={2}>  
              <TextField
                placeholder="UF"
                fullWidth
                margin="normal"
                name="uf"
                disabled={true}
                value={this.state.uf}
                onChange={this.onChange}
              />
            </Grid>  
          </Grid>

          <Grid container spacing={2}>
            <Grid item xs={12} sm={8}>  
              <TextField
                placeholder="Complemento"
                fullWidth
                margin="normal"
                name="complemento"
                maxLength="100"
                value={this.state.complemento}
                onChange={this.onChange}
              />
            </Grid>
            <Grid item xs={12} sm={4}>  
              <TextValidator
                placeholder="Número"
                fullWidth
                margin="normal"
                name="numero"
                validators={['required',]}
                errorMessages={['Número é obrigatório']}
                inputProps={{ maxLength: 8 }}
                value={this.state.numero}
                onChange={this.onChange}
              />
            </Grid>
          </Grid>

          <Grid container spacing={2}>
            <Grid item xs={12} sm={8}>
              <div>
                { this.state.telefones.map((telefone, id) => (
                  <div key={id}>
                    <Grid container spacing={2}>
                      <Grid item xs={12} sm={5}>
                        <Select
                          fullWidth
                          value={telefone.tipo}
                          displayEmpty
                          style={{marginTop: '15px'}}
                          onChange={this.handleTelefoneTipoChange(id)} >
                          <MenuItem value="" disabled>Tipo de telefone</MenuItem>  
                          <MenuItem value="residencial" inputProps={{selected: true}} selected={true}>Residencial</MenuItem>
                          <MenuItem value="celular">Celular</MenuItem>
                          <MenuItem value="comercial">Comercial</MenuItem>
                        </Select>
                      </Grid>
                      <Grid item xs={12} sm={5}>
                        <TextField
                          placeholder="Telefone"
                          fullWidth
                          margin="normal"
                          name="telefone"
                          maxLength="100"
                          value={telefone.numero}
                          onChange={this.handleTelefoneNumeroChange(id)}
                        />
                      </Grid>
                      <Grid item xs={12} sm={2}>
                        { id === 0 && <AddIcon style={{cursor:'pointer', marginTop: '15px'}} onClick={this.handleAddTelefone}/>}
                        { id !== 0 && <DeleteIcon style={{cursor:'pointer', marginTop: '15px'}} onClick={this.handleRemoveTelefone(id)}/>}
                      </Grid>
                    </Grid>
                  </div>
                )) } 
              </div>
            </Grid>
            <Grid item xs={12} sm={4}>
              <div>
                { this.state.emails.map((object, id) => (
                  <div key={id}>
                    <Grid container spacing={2}>
                      <Grid item xs={12} sm={9}>
                        <TextValidator
                          placeholder="E-mail"
                          fullWidth
                          margin="normal"
                          name="email"
                          validators={['required', 'isEmail']}
                          errorMessages={['E-mail é obrigatório', 'Informe um e-mail válido']}
                          inputProps={{maxLength: 100}}
                          value={object}
                          onChange={this.handleEmailChange(id)}
                        />
                      </Grid>
                      <Grid item xs={12} sm={3}>
                      { id === 0 && <AddIcon style={{cursor:'pointer', marginTop: '15px'}}  onClick={this.handleAddEmail}/>}
                        { id !== 0 && <DeleteIcon style={{cursor:'pointer', marginTop: '15px'}}  onClick={this.handleRemoveEmail(id)}/>}
                      </Grid>
                    </Grid> 
                  </div>
                )) }   
              </div>  
            </Grid>
          </Grid>
          
          <Button type="submit" variant="contained" style={{marginTop: '15px'}} color="primary">
            Salvar
          </Button>
        </ValidatorForm>
      </div>
    );
  }
}

const style = {
  display: 'flex',
  justifyContent: 'center'
};

export default EditarClienteComponent;
