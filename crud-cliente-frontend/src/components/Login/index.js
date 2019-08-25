import React, { Component } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { useStyles } from './styles';
import ApiService from '../../services/ApiService';

class LoginComponent extends Component {

  constructor(props) {
    super(props);
    const { classes } = props;
    this.classes = classes;
    this.state = {
      username: '',
      password: '',
      errorMessage: '',
    }
    this.handleChange = this.handleChange.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
  }

  handleChange(event) {
    this.setState(
      {
        [event.target.name] : event.target.value
      }
    )
  }

  handleLogin = async e => {
    e.preventDefault();
    const { username, password } = this.state;
    if(!username || !password) {
      this.setState({errorMessage: "Informe um login e senha"})
      return;
    }

    try {
      const response = await ApiService.login(username, password);
      ApiService.registerSuccesfullLogin(username, response.data);
      this.props.history.push('/clientes');
    } catch(err) {
      this.setState({
        error: "Usuário/senha inválida"
      })
    }
  }

  render() {
    return (
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={this.classes.paper}>
          <Avatar className={this.classes.avatar}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Entrar no sistema
          </Typography>
          <form className={this.classes.form} onSubmit={this.handleLogin}>
            {this.state.error && <p className={this.classes.erro}>{this.state.error}</p>}
            <TextField
              value={this.state.username} onChange={this.handleChange}
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="username"
              label="Usuário"
              name="username"
              autoFocus
            />
            <TextField
              value={this.state.password} onChange={this.handleChange}
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="password"
              label="Senha"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={this.classes.submit}
            >
              Entrar
            </Button>
          </form>
        </div>
      </Container>
    );
  }
}

export default withStyles(useStyles)(LoginComponent);