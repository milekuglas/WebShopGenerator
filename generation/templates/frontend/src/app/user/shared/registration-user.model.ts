export class RegistrationUser {
  firstName: string;
  lastName: string;
  address: string;
  phone: string;
  username: string;
  password: string;
  email: string;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
