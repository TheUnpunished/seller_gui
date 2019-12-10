package Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyRunnable implements Runnable{

    private Optional param;

    @Override
    public void run() {

    }
}
