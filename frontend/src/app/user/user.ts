import {LoginStatus} from "./loginStatus";

export interface User{
  userId: bigint;
  login: string;
  password: string;
  status: LoginStatus;
}
