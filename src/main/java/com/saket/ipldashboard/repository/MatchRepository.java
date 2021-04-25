package com.saket.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import com.saket.ipldashboard.model.Match;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> findByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and (m.date between :startDate and :endDate) order by date desc")
    List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /*
     * List<Match> findByTeam1OrTeam2AndDateBetweenOrderByDesc(String team1, String
     * team2, LocalDate date1, LocalDate date2);
     */

    default List<Match> findLatestMatchesByTeam(String teamName, int count) {

        Pageable pageable = PageRequest.of(0, count);
        return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);
    }

}