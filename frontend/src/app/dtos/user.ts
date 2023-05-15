export interface User {
  id?: number;
  admin?: boolean;
  email: string;
  firstName: string;
  lastName: string;
  birthdate: Date;
  address: string;
  areaCode: number;
  cityName: string;
  password: string;
}
