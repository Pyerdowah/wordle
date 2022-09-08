import {User} from "../user/user";
import {Word} from "../word/word";

export interface Statistic {
  numberOfTries: number;
  user: User;
  correctWord: Word;
}
