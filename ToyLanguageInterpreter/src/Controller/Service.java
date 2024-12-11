package Controller;

import Exceptions.MyException;
import Exceptions.StackException;
import Model.ProgState;
import Model.Value.Value;
import Utils.MyIHeap;
import Utils.MyIStack;
import Model.Statement.IStatement;
import Repository.IRepository;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Service {
    private IRepository repository;
    private ExecutorService executor;

    public Service(IRepository repo) {
        this.repository = repo;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public List<ProgState> removeCompletedPrg(List<ProgState> inProgList) {
        return inProgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    void oneStepForAllProg(List<ProgState> progList) throws InterruptedException {
        progList.forEach(prog -> {
            try {
                repository.logProgState(prog);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgState>> callList = progList.stream()
                .map((ProgState p) -> (Callable<ProgState>) (() -> {
                    try {
                        return p.oneStep();
                    } catch (MyException e) {
                        p.setNotCompleted(false);
                        System.out.println(e.getMessage());
                        return null;
                    }
                }))
                .collect(Collectors.toList());

        List<ProgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(p -> p != null && p.isNotCompleted())
                .collect(Collectors.toList());

        progList.addAll(newPrgList);

        progList.forEach(prg -> {
            try {
                repository.logProgState(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setProgList(progList);
    }

    public void allStep(){
        executor = Executors.newFixedThreadPool(2);
        List<ProgState> progList = removeCompletedPrg(repository.getProgList());

        while (progList.size() > 0) {
            MyIHeap<Integer, Value> heap = progList.get(0).getHeap();

            Set<Integer> usedAddresses = progList.stream()
                    .flatMap(p -> p.getUsedAddresses().stream())
                    .collect(Collectors.toSet());

            heap.setHeap(heap.safeGarbageCollector(usedAddresses, heap.getHeap()));

            try {
                oneStepForAllProg(progList);
            } catch (InterruptedException e) {
                throw new MyException("Execution interrupted");
            }

            progList = removeCompletedPrg(repository.getProgList());
        }

        executor.shutdownNow();
        repository.setProgList(progList);
    }
}
