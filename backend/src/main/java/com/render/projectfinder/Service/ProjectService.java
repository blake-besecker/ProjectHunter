package com.render.projectfinder.Service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.render.projectfinder.Entity.Project;
import com.render.projectfinder.Entity.Tag;
import com.render.projectfinder.Repository.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // public List<String> cleanTagList(List<String> taglist) {
    //     List<String> cleanedTagList = new ArrayList<>();
    //     List<String> allTags = projectRepository.getAllTags().stream().map(tag -> tag.toLowerCase()).collect(Collectors.toList());;
    //     for (int i = 0; i<taglist.size();i++) {
    //         if (allTags.contains(taglist.get(i).toLowerCase())) {
    //             cleanedTagList.add(taglist.get(i));
    //         }
    //     }
    //     return cleanedTagList;
    // }
    @Transactional(readOnly = true)
    public List<Project> getProjectsFromTags(List<String> taglist) {
        String[] tags = taglist.toArray(new String[0]);

        String cleanTag;

        Tag[] dbTags = getAllTags().toArray(new Tag[0]); //questionable

        HashSet<String> finalTagList = new HashSet<>(); //final formatted list of tags with invalids removed

        for (String tag : tags) {
            cleanTag = tag.replaceAll("[^a-zA-Z0-9_]", "");
            cleanTag = cleanTag.toLowerCase();

            for (int i = 0; i<dbTags.length; i++) {
                if (cleanTag.equals(dbTags[i].getName().toLowerCase())) {
                    finalTagList.add(cleanTag);
                }
            }
            
        }

        String[] finalTagArray = finalTagList.toArray(new String[0]);

        return projectRepository.findProjectsByAllTags(finalTagArray);
    }
    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return projectRepository.getAllTags();
    }
}
