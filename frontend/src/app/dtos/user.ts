export interface User {
  id?: number;
  admin: boolean;
  email: string;
  firstName: string;
  lastName: string;
  birthdate: Date;
  address: string;
  areaCode: number;
  cityName: string;
  password: string;
  isLocked: boolean;
}

export interface BlockUser {
  email: string;
  isLocked: boolean;
}

export class BlockUser {
  constructor(public email: string,
              public isLocked: boolean) {
  }

}
