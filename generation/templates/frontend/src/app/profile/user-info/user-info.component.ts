import {Component, OnInit} from '@angular/core';
import {UserService} from '../../user/shared/user.service';
import {User} from '../../user/shared/user.model';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  user: User = new User();

  constructor(private userService: UserService) {
  }

  ngOnInit() {

    this.userService.getUserData().subscribe(user => {
      this.user = user;
    });
  }

  get name() {
    return this.user.firstName + ' ' + this.user.lastName;
  }

  get email() {
    return this.user.email;
  }
}
